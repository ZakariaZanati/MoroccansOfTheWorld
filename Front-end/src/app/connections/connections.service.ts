import { Injectable } from '@angular/core';
import {JoinRequest} from '../groups/join-requests/join-request.payload';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ConnectionsService {

  constructor(private http : HttpClient) { }

  sendConnectionRequest(id : number){
    return this.http.get('http://localhost:8181/api/connections/request/'+id);
  }

  viewConnectionRequests():Observable<Array<JoinRequest>>{
    return this.http.get<Array<JoinRequest>>('http://localhost:8181/api/connections/requests');
  }

  connectionStatus(username : string){
    return this.http.get('http://localhost:8181/api/connections/status/'+username,{responseType : 'text'});
  }

  respondToRequest(id : number,response : FormData){
    return this.http.post('http://localhost:8181/api/connections/respond/'+id,response);
  }

  getAllConnections():Observable<Array<JoinRequest>>{
    return this.http.get<Array<JoinRequest>>('http://localhost:8181/api/connections');
  }


}
