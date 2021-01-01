import { Component, OnInit } from '@angular/core';
import {NotificationModel} from './notification.payload';
import {NotificationsService} from './notifications.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {


  show : boolean = false;
  new : boolean = false;
  count : number = 0;
  notifications : Array<NotificationModel> = [];
  ids : Array<number> = [];

  constructor(private notificationService : NotificationsService) { }

  ngOnInit(): void {

    this.notificationService.getNotifications().subscribe(data => {
      console.log(data)
      this.notifications = data;
      this.notifications.map(notification => {
        if (!notification.seen) {
          this.count++;
          this.new = true;
          this.ids.push(notification.id);
        }
      })
    })
  }

  showNotifications(){
    this.show = !this.show;
    this.new = false;
    this.ids.map(id => {
      this.notificationService.setNotificationSeen(id).subscribe(data => {
        console.log(data);
        
      });
    })

    this.ids = [];

  }

}
