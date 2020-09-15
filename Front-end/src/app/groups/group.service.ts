import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {GroupModel} from './group-model';
import {GroupResponse} from './group-response';
import {PostModel} from '../shared/post-model';
import {JoinRequest} from './join-requests/join-request.payload';


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

  getGroupsPage(pageNumber : number,pageSize : number,name : string):Observable<GroupResponse>{

    let params = new HttpParams();
    params = params.append('page',pageNumber.toString());
    params = params.append('size', pageSize.toString());
    params = params.append('name',name);

    return this.http.get<GroupResponse>('http://localhost:8181/api/groups/pageable',{params: params});
  }

  getGroup(id : number):Observable<GroupModel>{
    return this.http.get<GroupModel>('http://localhost:8181/api/groups/'+id)
  }

  getGroupPosts(id : number):Observable<Array<PostModel>>{
    return this.http.get<Array<PostModel>>('http://localhost:8181/api/posts/group/'+id)
  }

  sendRequest(id : number){
    return this.http.get('http://localhost:8181/api/groups/request/'+id);
  }

  public getRequestStatus(id : number){
    return this.http.get('http://localhost:8181/api/groups/request/status/'+id,{responseType : 'text'});
  }

  getAllRequests(id : number):Observable<Array<JoinRequest>>{
    return this.http.get<Array<JoinRequest>>('http://localhost:8181/api/groups/requests/'+id);
  }

  respondToRequest(id : number,response : FormData){
    return this.http.post('http://localhost:8181/api/groups/respond/'+id,response);
  }
}
