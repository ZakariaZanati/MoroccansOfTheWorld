import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {GroupModel} from './group-model';
import {PostModel} from '../shared/post-model';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(private http:HttpClient) { }

  createGroup(createformData : FormData){
    return this.http.post('http://localhost:8181/api/groups',createformData).subscribe(data => {
      console.log(data)
    },err => {
      throwError(err);
    })
  }

  getAllGroups():Observable<Array<GroupModel>>{
    return this.http.get<Array<GroupModel>>('http://localhost:8181/api/groups');
  }

  getGroup(id : number):Observable<GroupModel>{
    return this.http.get<GroupModel>('http://localhost:8181/api/groups/'+id)
  }

  getGroupPosts(id : number):Observable<Array<PostModel>>{
    return this.http.get<Array<PostModel>>('http://localhost:8181/api/posts/group/'+id)
  }
}
