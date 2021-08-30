import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, NgForm } from '@angular/forms';
import {SignupRequestPayload} from './signup-request.payload';
import {AuthService} from '../shared/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['../login/login.component.css','./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupRequestPayload : SignupRequestPayload;
  signupForm : FormGroup;
  isError : boolean = false;
  error : string = null;
  userType : string = '';
  types : string[] = ['Candidat','Provider']

  constructor(private authService : AuthService,private router: Router,
    private toastr : ToastrService) {
    this.signupRequestPayload = {
      username : '',
      email : '',
      password : '',
      userType : ''

    }
   }

  ngOnInit(): void {
    this.signupForm = new FormGroup({
      username : new FormControl('',Validators.required),
      email : new FormControl('',[Validators.required,Validators.email]),
      password : new FormControl('',Validators.required),
      userType : new FormControl('',Validators.required)
    })
  }

  signup(){
    this.signupRequestPayload.email = this.signupForm.get('email').value;
    this.signupRequestPayload.password = this.signupForm.get('password').value;
    this.signupRequestPayload.username = this.signupForm.get('username').value;
    this.signupRequestPayload.userType = this.signupForm.get('userType').value;

    this.authService.signup(this.signupRequestPayload)
      .subscribe(() => {
        this.router.navigate(['/login'],
          {queryParams:{registered:'true'}})
      },()=>{
        this.toastr.error('Registration Failed ! Please try again');
        
      })
  }

  onSubmit(form : NgForm){

    if (form.value.confirmation != form.value.password) {
      this.isError = true;
      this.error = "Password confirmation error ! Please try again";
      return;
    }

    console.log(form);
    this.signupRequestPayload.email = form.value.email;
    this.signupRequestPayload.password = form.value.password;
    this.signupRequestPayload.username = form.value.username;
    this.signupRequestPayload.userType = form.value.userType.toLowerCase();

    this.authService.signup(this.signupRequestPayload)
      .subscribe(() => {
        this.router.navigate(['/login'],
          {queryParams:{registered:'true'}})
      },()=>{
        this.isError = true;
        this.error = 'Email or Username already used';
      })

      form.reset();
  }

}
