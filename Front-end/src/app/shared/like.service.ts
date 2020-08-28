import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class LikeService {

  constructor(private http : HttpClient) { }

  like(postId : number):Observable<any>{
    return this.http.post('http://localhost:8181/api/likes/',postId);
  }
}
