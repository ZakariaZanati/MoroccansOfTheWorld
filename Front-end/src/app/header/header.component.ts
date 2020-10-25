import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/shared/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn:boolean;
  username:string;
  fullName : string;
  image : any;
  response : any;

  classes:string[] = ['sidebar', 'show']
  show : boolean = false;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {

    this.authService.loggedIn.subscribe((data:boolean) => this.isLoggedIn = data);
    this.authService.username.subscribe((data:string) => this.username = data);
    
    this.isLoggedIn = this.authService.isLoggedIn();
    this.username = this.authService.getUserName();
    this.fullName = this.authService.fullName();

    this.authService.getUserImage().subscribe(data => {
        this.response = data;
        this.image =  'data:image/jpeg;base64,'+this.response.picByte;
    })
  }

  logout(){
    this.authService.logout();
    this.isLoggedIn = false;
    this.router.navigateByUrl('');
  }

  toggleClass(){
    this.show = !this.show
  }

  homePage(){
    this.router.navigateByUrl('');
  }

}
