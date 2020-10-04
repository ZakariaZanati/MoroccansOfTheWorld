import { Component, OnInit } from '@angular/core';
import {ConnectionsService} from './connections.service';
import {JoinRequest} from '../groups/join-requests/join-request.payload';

@Component({
  selector: 'app-connections',
  templateUrl: './connections.component.html',
  styleUrls: ['./connections.component.css']
})
export class ConnectionsComponent implements OnInit {

  count : number;
  show : boolean = false;
  new : boolean = false;

  requests : Array<JoinRequest> = [];
  
  constructor(private connectionsService : ConnectionsService) { }

  ngOnInit(): void {

    this.getRequests();
  }

  showRequests(){
    this.show = !this.show;
  }

  getRequests(){
    this.connectionsService.viewConnectionRequests().subscribe(data => {
      this.requests = data;
      this.requests.map(request => {
        const img = request.image;
        if (img) {
          request.image = "data:image/jpeg;base64,"+request.image;
        }
      })
    })
  }

  requestResponse(id : number,response : string){
    let res = new FormData();
    res.append('response',response);
    this.connectionsService.respondToRequest(id,res).subscribe(data => {
      console.log(data);
      this.getRequests();
    })
  }
}
