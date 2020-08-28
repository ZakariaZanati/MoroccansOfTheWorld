import { Injectable } from '@angular/core';
import { CommentPayload } from './comment.payload';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http:HttpClient) { }

  postComment(commentPayload:CommentPayload):Observable<any>{
    return this.http.post<any>('http://localhost:8181/api/comments/', commentPayload)
  }

  getAllCommentsForPost(postId: number): Observable<CommentPayload[]> {
    return this.http.get<CommentPayload[]>('http://localhost:8181/api/comments/by-post/' + postId);
  }
}
