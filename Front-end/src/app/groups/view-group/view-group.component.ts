import { Component, OnInit } from '@angular/core';
import {GroupModel} from '../group-model';
import {GroupService} from '../group.service';
import { Router,ActivatedRoute } from '@angular/router';
import { PostModel } from 'src/app/shared/post-model';
import {AuthService} from '../../auth/shared/auth.service';
import {UserInfos} from '../../shared/user-info';

@Component({
  selector: 'app-view-group',
  templateUrl: './view-group.component.html',
  styleUrls: ['./view-group.component.css']
})
export class ViewGroupComponent implements OnInit {

  group : GroupModel;
  id : number;
  posts : Array<PostModel> = [];
  members : Array<UserInfos> = [];

  requestStatus : string;
  admin : string;
  userType : string;

  username : string;
  selectedFile : File;
  retrievedImage : any;
  base64Data: any;
  retrieveResponse: any;
  imageName: any;
  firstName : String;
  lastName : String;
  currentJob? : String;
  isAdmin : boolean;
  constructor(private groupService : GroupService,private activatedRoute: ActivatedRoute,private authService : AuthService) {
      
   }

  ngOnInit(): void {
    this.id = this.activatedRoute.snapshot.params.id;
    this.groupService.getGroup(this.id).subscribe(data => {
      this.group = data;
      this.admin = data.adminUserName;
      console.log(this.admin)
      if (data.imageBytes) {
        this.group.imageBytes = "data:image/jpeg;base64,"+this.group.imageBytes;
      }

      this.authService.getUserByUserName(this.admin).subscribe(data => {
        this.username = data.username;
        this.firstName = data.firstName;
        this.lastName = data.lastName;
        this.currentJob = data.currentJob;
      })
      
      this.authService.getImageByUsername(this.admin).subscribe(data => {
        this.retrieveResponse = data
        this.base64Data = this.retrieveResponse.picByte;
        this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
      })
      if (this.authService.getUserName() == this.admin) {
        this.requestStatus = 'JOINED';
        this.isAdmin = true;
      }
      else{
      this.groupService.getRequestStatus(this.id).subscribe(status => {
        this.requestStatus = status;
        console.log(status)
      })
    }

    });
    

    this.groupService.getGroupPosts(this.id).subscribe(data => {
      this.posts = data;
      this.posts.map(post =>{
        const img = post.image;
        post.profileImage = "data:image/jpeg;base64,"+post.profileImage;
        if (img) {
          post.image = "data:image/jpeg;base64,"+post.image;
        }
      });
    });

    this.groupService.getGroupMembers(this.id).subscribe((users : UserInfos[]) => {
      this.members = users;
      console.log("group members: \n "+users)
      this.members.map( member => {
        console.log(member)
        member.image = "data:image/jpeg;base64,"+member.image
      })
    })
    

    
  }

  joinGroup(){

    this.groupService.sendRequest(this.id).subscribe(data => console.log(data));
    this.requestStatus = 'REQUESTED';
  }



}
