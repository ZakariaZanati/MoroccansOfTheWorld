import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-connections',
  templateUrl: './connections.component.html',
  styleUrls: ['./connections.component.css']
})
export class ConnectionsComponent implements OnInit {

  count : number;
  show : boolean = false;
  new : boolean = false;
  
  constructor() { }

  ngOnInit(): void {
  }

  showRequests(){

  }

}
