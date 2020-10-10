import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import {UserVerification} from './user-verification.payload';
import {VerificationService} from './verification.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.css']
})
export class VerificationComponent implements OnInit {

  users : Array<UserVerification> = []

  constructor(private verificationService : VerificationService,private router:Router) { }

  ngOnInit(): void {

    this.getRequests();

  }

  getRequests(){
    this.verificationService.getAllVerificationRequests().subscribe(data => {
      this.users = data;
      console.log(data);
    },err => throwError(err));
  }

  respondToRequest(id : number,response : string){
    this.verificationService.setUserVerification(id,response).subscribe(response => {
      console.log(response);
      this.getRequests();
    },err => throwError(err));
  }

  goToProfile(username : string){
    this.router.navigateByUrl('/profile/'+username);
  }

}
