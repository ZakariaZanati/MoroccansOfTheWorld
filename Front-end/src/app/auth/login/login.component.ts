import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, NgForm } from '@angular/forms';
import {LoginRequestPayload} from './login.request.payload';
import {AuthService} from '../shared/auth.service';
import { Router,ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  loginRequestPayload : LoginRequestPayload;
  registerSuccessMessage: String;
  isError : Boolean = false;

  constructor(private authService : AuthService,private router: Router,
    private toastr : ToastrService,private activatedRoute : ActivatedRoute) { 
    this.loginRequestPayload = {
      username : '',
      password : ''
    };
  }

  ngOnInit(): void {

    /*

    this.activatedRoute.queryParams.subscribe(params => {
        if (params.registered !== undefined && params.registered === 'true') {
          console.log(params)
          this.toastr.success('Signup Successful');
          this.registerSuccessMessage = 'Please Check your inbox for activation email '
            + 'activate your account before you Login!';
        }
      });
      */
  }

  onSubmit(form : NgForm){
    if (!form.valid) {
      return;
    }
    this.loginRequestPayload.username = form.value.username;
    this.loginRequestPayload.password = form.value.password;

    this.authService.login(this.loginRequestPayload).subscribe(data => {
      console.log(data)
      if (data) {
        if (this.authService.isCompleted()) {
          this.router.navigateByUrl('/newsfeed');
        } else {
          this.router.navigateByUrl('/userdetails');
        }
      } else {
        this.isError = true;
      }
    },error => {
      this.isError = true;
    })

    form.reset();

  }

}
