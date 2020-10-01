import { Component, OnInit } from '@angular/core';
import {AuthService} from '../auth/shared/auth.service';
import {PostService} from '../shared/post.service';
import {PostModel} from '../shared/post-model';
import { HttpClient } from '@angular/common/http';
import { Router,ActivatedRoute } from '@angular/router';
import {ConnectionsService} from '../connections/connections.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  username : string;
  email : string;
  selectedFile : File;
  retrievedImage : any;
  base64Data: any;
  retrieveResponse: any;
  message: string;
  imageName: any;
  firstName : String;
  lastName : String;
  birthDate? : Date;
  phoneNumber? :String;
  currentJob? : String;
  website? : String;
  aboutMe? : String;
  country? : String;
  city ?: String;
  name : string;
  id : number;

  connectionStatus : string;
  posts : Array<PostModel> = [];

  constructor(private activatedRoute: ActivatedRoute, private authService : AuthService,private httpClient : HttpClient,
    private router:Router,private postService : PostService,private connectionService : ConnectionsService) { }

  ngOnInit(): void {
    this.name = this.activatedRoute.snapshot.params.name;
    if (this.name == this.authService.getUserName()) {
      this.router.navigateByUrl('/profile')
    }
    this.authService.getUserByUserName(this.name).subscribe(data => {
      this.id = data.userId;
      this.username = data.username;
      this.email = data.email;
      this.firstName = data.firstName;
      this.lastName = data.lastName;
      this.phoneNumber = data.phoneNumber;
      this.birthDate = data.birthDate;
      this.city = data.city;
      this.country = data.country;
      this.website = data.website;
      this.currentJob = data.currentJob;
      this.aboutMe = data.aboutMe;
    })
    
    this.authService.getImageByUsername(this.name).subscribe(data => {
      this.retrieveResponse = data
      this.base64Data = this.retrieveResponse.picByte;
      this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
    })

    this.postService.getAllPostsByUser(this.name).subscribe(post =>{
      this.posts = post;
      this.posts.map(post =>{
        const img = post.image;
        post.profileImage = "data:image/jpeg;base64,"+post.profileImage;
        if (img) {
          post.image = "data:image/jpeg;base64,"+post.image;
        }
      })
    });

    this.getConnectionStatus();
  }

  snedConnectionRequest(){
    this.connectionService.sendConnectionRequest(this.id).subscribe(data => {
      console.log(data);
      this.getConnectionStatus();
    })
  }

  getConnectionStatus(){
    this.connectionService.connectionStatus(this.activatedRoute.snapshot.params.name).subscribe(data => {
      this.connectionStatus = data;
      console.log(data);
    })
  }



}
