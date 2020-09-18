import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {


  show : boolean = false;
  new : boolean = true;
  count : number = 2;

  constructor() { }

  ngOnInit(): void {
  }

  showNotifications(){
    this.show = !this.show;
    this.new = false;
  }

}
