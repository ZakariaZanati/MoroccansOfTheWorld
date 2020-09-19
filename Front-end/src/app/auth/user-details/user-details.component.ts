import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import {AuthService} from '../shared/auth.service';
import { Router,ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import {UserDetailsPayload} from './user-details.payload'
import { Observable } from 'rxjs';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  userDetailsPayload : Observable<UserDetailsPayload>;
  userDetailsForm : FormGroup;
  verification : string;

  constructor(private activatedRoute : ActivatedRoute, private authService : AuthService,private router: Router,
    private toastr : ToastrService) {
      
    }

   ngOnInit() :void {

    this.verification = this.activatedRoute.snapshot.params.verification;

      this.userDetailsForm = new FormGroup({
        firstName : new FormControl(''),
        lastName : new FormControl(''),
        phoneNumber : new FormControl(''),
        birthDate : new FormControl(''),
        currentJob : new FormControl(''),
        website  : new FormControl(''),
        aboutMe : new FormControl(''),
        country : new FormControl(''),
        city : new FormControl('')
    })
    this.authService.getUserDetails().subscribe(data => {
      this.userDetailsForm.patchValue(data)
      console.log(data)
    });
    console.log(this.userDetailsForm.value)
    
  }

  userDetails(){

    this.authService.userDetails(this.userDetailsForm.value).subscribe(()=>{

      if(this.verification){
        this.authService.requestVerification().subscribe(response => console.log(response));
      }
      this.router.navigate(['/profile'])
    })
  }

}
