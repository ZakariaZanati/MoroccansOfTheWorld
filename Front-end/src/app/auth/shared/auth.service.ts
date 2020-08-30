import { Injectable,Output,EventEmitter } from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {SignupRequestPayload} from '../signup/signup-request.payload';
import {LoginRequestPayload} from '../login/login.request.payload'
import {LoginResponse} from '../login/login.response.payload'
import {CurrentUserModel} from '../user-profile/current-user.model';
import {UserDetailsPayload} from '../user-details/user-details.payload';

import { Observable, throwError } from 'rxjs';
import { LocalStorageService } from 'ngx-webstorage';
import { map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  @Output() loggedIn: EventEmitter<Boolean> = new EventEmitter<Boolean>();
  @Output() username: EventEmitter<String> = new EventEmitter<String>()


  refreshTokenPayload = {
    refreshToken: this.getRefreshToken(),
    username: this.getUserName()
  }

  constructor(private httpClient : HttpClient,
    private localStorage : LocalStorageService) {

  }

  signup(signupRequestPayload : SignupRequestPayload):Observable<any>{
    return this.httpClient.post('http://localhost:8181/api/auth/signup',signupRequestPayload,{responseType:'text'})
  }

  login(loginRequestPayload : LoginRequestPayload):Observable<Boolean>{
    return this.httpClient.post<LoginResponse>('http://localhost:8181/api/auth/login',
    loginRequestPayload).pipe(map(data => {
        this.localStorage.store('authenticationToken',data.authenticationToken);
        this.localStorage.store('username',data.username);
        this.localStorage.store('refreshToken',data.refreshToken);
        this.localStorage.store('expiresAt',data.expiresAt);
        this.localStorage.store('completed',data.completed);

        this.loggedIn.emit(true);
        this.username.emit(data.username);

        return true;
      }));

  }
  userDetails(userDetailsPayload : UserDetailsPayload){
    return this.httpClient.post<UserDetailsPayload>('http://localhost:8181/api/user/infos',userDetailsPayload)
  }

  refreshToken() {
    return this.httpClient.post<LoginResponse>('http://localhost:8181/api/auth/refresh/token',
      this.refreshTokenPayload)
      .pipe(tap(response => {
        this.localStorage.clear('authenticationToken');
        this.localStorage.clear('expiresAt');

        this.localStorage.store('authenticationToken',
          response.authenticationToken);
        this.localStorage.store('expiresAt', response.expiresAt);
      }));
  }

  logout(){
    this.httpClient.post('http://localhost:8181/api/auth/logout',this.refreshTokenPayload,
    {responseType:'text'})
    .subscribe(data => {
      console.log(data);
    },error => {
      throwError(error);
    });
    this.localStorage.clear('authenticationToken');
    this.localStorage.clear('username');
    this.localStorage.clear('refreshToken');
    this.localStorage.clear('expiresAt');

  }

  getCurrentUserInfo():Observable<CurrentUserModel>{
    return this.httpClient.get<CurrentUserModel>('http://localhost:8181/api/user/profile')
  }

  getUserName(){
    return this.localStorage.retrieve('username');
  }

  getJwtToken(){
    return this.localStorage.retrieve('authenticationToken');
  }

  getRefreshToken() {
    return this.localStorage.retrieve('refreshToken');
  }

  isLoggedIn(): boolean {
    return this.getJwtToken() != null;
  }

  isCompleted(): boolean {
    return this.localStorage.retrieve('completed');
  }

}
