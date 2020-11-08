import { Component, OnInit } from '@angular/core';
import {AuthService} from '../shared/auth.service';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import {PostModel} from '../../shared/post-model';
import {PostService} from '../../shared/post.service';
import {ConnectionsService} from '../../connections/connections.service';
import { JoinRequest } from 'src/app/groups/join-requests/join-request.payload';



@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

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
  aboutMe? : string;
  country? : String;
  city ?: String;
  id : number;

  isVerified : boolean;
  isLoaded : boolean;
  editable : boolean = false;
  updateAboutMe : string;
  option = 1;

  posts : Array<PostModel> = [];
  connections : Array<JoinRequest> = [];

  constructor(private authService : AuthService,private httpClient : HttpClient,private router:Router,
    private postService : PostService,private connectionService : ConnectionsService) {
      this.postService.getAllPostsByCurrentUser().subscribe(post =>{
        this.posts = post;
        this.posts.map(post =>{
          const img = post.image;
          post.profileImage = "data:image/jpeg;base64,"+post.profileImage;
          if (img) {
            post.image = "data:image/jpeg;base64,"+post.image;
          }
        })
      });
     }

  ngOnInit(): void {

    this.isVerified = this.authService.isVerified();

    this.authService.getCurrentUserInfo().subscribe(data => {
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
      this.id = data.userId;
      this.isLoaded = true;
      console.log("user id is"+this.id);
      
    })
    this.getImage();
    this.getConnections();
  }

  public onFileChanged(event) {
    //Select File
    this.selectedFile = event.target.files[0];
  }

    onUpload() {
      
      //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
        const uploadImageData = new FormData();
        uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
      
        //Make a call to the Spring Boot Application to save the image
        this.httpClient.post('http://localhost:8181/api/user/img', uploadImageData, { observe: 'response' })
          .subscribe((response) => {
            if (response.status === 200) {
              this.message = 'Image uploaded successfully';
              console.log("image uploaded")
              window.location.reload()
              
            } else {
              this.message = 'Image not uploaded successfully';
            }
          }
          );
    }
  updateForm(){
    this.router.navigateByUrl('/userdetails')
  }


  getImage(){
    this.httpClient.get('http://localhost:8181/api/user/profile/img')
        .subscribe(
          res => {
            this.retrieveResponse = res;
            this.base64Data = this.retrieveResponse.picByte;
            this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
          }
        );
  }

  editAboutMe(){
    console.log(this.editable)
    this.editable = true;
  }

  saveAboutMe(){
    let aboutme = new FormData();
    aboutme.append('aboutme',this.aboutMe);
    this.authService.setAboutMe(aboutme).subscribe(data => console.log(data),err => console.log(err));
    window.location.reload();
  }

  changeOption(opt : number){
    this.option = opt;
  }

  getConnections(){
    this.connectionService.getAllConnections().subscribe(data => {
      this.connections = data;
      this.connections.map(connection => {
        const img = connection.image;
        if (img) {
          connection.image = "data:image/jpeg;base64,"+connection.image;
        }
      })
    });
  }

  goToProfile(username : string){
    this.router.navigateByUrl('/profile/'+username);
  }


}
