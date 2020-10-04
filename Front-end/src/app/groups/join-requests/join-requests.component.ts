import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {GroupService} from '../group.service';
import {JoinRequest} from './join-request.payload';

@Component({
  selector: 'app-join-requests',
  templateUrl: './join-requests.component.html',
  styleUrls: ['./join-requests.component.css']
})
export class JoinRequestsComponent implements OnInit {

  id : number;
  requests : Array<JoinRequest> = [];
  constructor(private activatedRoute : ActivatedRoute,private groupService : GroupService) { }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params.id;
    this.getRequests();
  }

  getRequests(){
    this.groupService.getAllRequests(this.id).subscribe(data =>{
      this.requests = data;
      this.requests.map(request => {
        const img = request.image;
        if (img) {
          request.image = "data:image/jpeg;base64,"+request.image;
        }
      })
      console.log(this.requests)
    })

  }

  requestResponse(username : string,response : string){
    const responseForm = new FormData();
    responseForm.append('userName',username);
    responseForm.append('response',response);
    console.log(username)
    this.groupService.respondToRequest(this.id,responseForm).subscribe(data => {console.log(data)});
    this.getRequests();

  }


}
