import { Component, OnInit } from '@angular/core';
import {AuthService} from '../shared/auth.service';
import { HttpClient, HttpEventType } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import {PostModel} from '../../shared/post-model';
import {PostService} from '../../shared/post.service';



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
  aboutMe? : String;
  country? : String;
  city ?: String;

  isVerified : boolean;

  posts : Array<PostModel> = [];

  constructor(private authService : AuthService,private httpClient : HttpClient,private router:Router,
    private postService : PostService) {
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
    })
    this.getImage();
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

}
