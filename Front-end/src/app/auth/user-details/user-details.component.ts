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
      
    }

  ngOnInit(): void {

    this.userDetailsForm = new FormGroup({
      firstName : new FormControl('',Validators.required),
      lastName : new FormControl('',Validators.required)
      
    })
  }

}
