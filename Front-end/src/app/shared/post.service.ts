import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CreatePostPayload } from '../post/create-post/create-poste.payload';
import {PostModel} from './post-model';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http:HttpClient) { }

  createPost(postPayload : CreatePostPayload){
    const uploadPost = new FormData();
    uploadPost.append('postImage',postPayload.selectedFile);
    uploadPost.append('postDescription',postPayload.description);
    console.log(uploadPost.getAll('postImage'))
    return this.http.post('http://localhost:8181/api/posts/',postPayload,{ observe: 'response' });
  }

  getAllPosts():Observable<Array<PostModel>>{
    return this.http.get<Array<PostModel>>('http://localhost:8181/api/posts/');
  }


}
