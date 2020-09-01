import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class LikeService {

  constructor(private http:HttpClient) {
  }

  postLike(postId: number){
    return this.http.post('http://localhost:8181/api/likes/',postId);
  }
}
