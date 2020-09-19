import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {UserVerification} from './user-verification.payload';

@Injectable({
  providedIn: 'root'
})
export class VerificationService {

  constructor(private http : HttpClient) { }

  getAllVerificationRequests():Observable<Array<UserVerification>>{
    return this.http.get<Array<UserVerification>>('http://localhost:8181/api/admin/verification')
  }

  setUserVerification(id : number,response : string){
    let params = new HttpParams();
    params.append('response',response);
    return this.http.post('http://localhost:8181/api/admin/verification/'+id,{params:params});
  }
}
