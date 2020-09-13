import { Component, OnInit } from '@angular/core';
import {GroupModel} from '../group-model';
import {GroupService} from '../group.service';
import {AuthService} from '../../auth/shared/auth.service'

@Component({
  selector: 'app-list-group',
  templateUrl: './list-group.component.html',
  styleUrls: ['./list-group.component.css']
})
export class ListGroupComponent implements OnInit {

  showForm : boolean = false;
  groups : Array<GroupModel> = [];
  userType : string;

  constructor(private groupService : GroupService,private authService : AuthService) {

    this.userType = authService.getUserType();

    this.groupService.getAllGroups().subscribe(data => {
      this.groups = data
      this.groups.map(group => {
        const img = group.imageBytes;
        if (img) {
          group.imageBytes = "data:image/jpeg;base64,"+group.imageBytes
        }
      })
    })
   }

  ngOnInit(): void {
  }

  addGroup(){
    this.showForm = !this.showForm
  }

}
