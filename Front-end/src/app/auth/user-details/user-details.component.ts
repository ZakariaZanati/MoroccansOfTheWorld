import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {AuthService} from '../shared/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import {UserDetailsPayload} from './user-details.payload'

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  userDetailsPayload : UserDetailsPayload;
  userDetailsForm : FormGroup;

  constructor(private authService : AuthService,private router: Router,
    private toastr : ToastrService) { 
      this.userDetailsPayload = {
        firstName : new String(),
        lastName : new String(),
        birthDate : new Date(),
        phoneNumber : new String(),
        currentJob : new String(),
        website : new String(),
        aboutMe : new String(),
        country : new String(),
        city : new String()
      }
    }

  ngOnInit(): void {

    this.userDetailsForm = new FormGroup({
      firstName : new FormControl('',Validators.required),
      lastName : new FormControl('',Validators.required),
      phoneNumber : new FormControl(''),
      birthDate : new FormControl(''),
      currentJob : new FormControl(''),
      website  : new FormControl(''),
      aboutMe : new FormControl(''),
      country : new FormControl(''),
      city : new FormControl('')
    })
  }

  userDetails(){
    this.userDetailsPayload.firstName = this.userDetailsForm.get('firstName').value;
    this.userDetailsPayload.lastName = this.userDetailsForm.get('lastName').value;
    this.userDetailsPayload.phoneNumber = this.userDetailsForm.get('phoneNumber').value;
    this.userDetailsPayload.birthDate = this.userDetailsForm.get('birthDate').value;
    this.userDetailsPayload.currentJob = this.userDetailsForm.get('currentJob').value;
    this.userDetailsPayload.website = this.userDetailsForm.get('website').value;
    this.userDetailsPayload.aboutMe = this.userDetailsForm.get('aboutMe').value;
    this.userDetailsPayload.country = this.userDetailsForm.get('country').value;
    this.userDetailsPayload.city = this.userDetailsForm.get('city').value;

    this.authService.userDetails(this.userDetailsPayload).subscribe(()=>{
      this.router.navigate(['/newsfeed'])
    })
  }

}
