import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-list-group',
  templateUrl: './list-group.component.html',
  styleUrls: ['./list-group.component.css']
})
export class ListGroupComponent implements OnInit {

  showForm : boolean = false;
  constructor() { }

  ngOnInit(): void {
  }

  addGroup(){
    this.showForm = !this.showForm
  }

}
