import { Component, OnInit } from '@angular/core';
import {AuthService} from '../shared/auth.service';
import { HttpClient, HttpEventType } from '@angular/common/http';


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

  constructor(private authService : AuthService,private httpClient : HttpClient) { }

  ngOnInit(): void {

    this.authService.getCurrentUserInfo().subscribe(data => {
      this.username = data.username;
      this.email = data.email;
    })
    this.httpClient.get('http://localhost:8181/api/user/profile/img')
      .subscribe(
        res => {
          console.log(res)
          this.retrieveResponse = res;
          this.base64Data = this.retrieveResponse.picByte;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        }
      );
  }

  public onFileChanged(event) {
    //Select File
    this.selectedFile = event.target.files[0];
  }

  onUpload() {
    console.log(this.selectedFile);
    
    //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadImageData = new FormData();
    uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);
  
    //Make a call to the Spring Boot Application to save the image
    this.httpClient.post('http://localhost:8181/api/user/img', uploadImageData, { observe: 'response' })
      .subscribe((response) => {
        if (response.status === 200) {
          this.message = 'Image uploaded successfully';
        } else {
          this.message = 'Image not uploaded successfully';
        }
      }
      );
  }
  /*
  getImage() {
    //Make a call to Sprinf Boot to get the Image Bytes.
    console.log("get image")
    this.httpClient.get('http://localhost:8181/api/user/profile/img')
      .subscribe(
        res => {
          console.log(res)
          this.retrieveResponse = res;
          this.base64Data = this.retrieveResponse.picByte;
          this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
        }
      );
  }*/







}
