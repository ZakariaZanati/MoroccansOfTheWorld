import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import {ConnectionsService} from '../connections/connections.service';
import {JoinRequest} from '../groups/join-requests/join-request.payload';
import {UsersResponse} from './users-response';
import {UserModel} from './users-model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {


  users : Array<UserModel> = [];
  pageNumber : number = 0;
  totalPages : number = 0;
  currentSelectedPage : number = 0;
  pageIndexes : Array<number> = [];
  name : string = "";
  city : string = "";
  country : string = "";

  constructor(private connectionsService : ConnectionsService,private router : Router) { }

  ngOnInit(): void {
    this.getPage(0,18,this.name,this.city,this.country);
  }

  getPage(page : number,size : number,name : string,city : string, country : string){
    this.connectionsService.getUsersPage(page,size,name,city,country).subscribe((response : UsersResponse) => {
      this.users = response.users;
      console.log(response)
      this.users.map(user => {
        const img = user.image;
        if (img) {
          user.image = "data:image/jpeg;base64,"+user.image;
        }
        this.totalPages = response.totalPages;
        this.pageIndexes = Array(this.totalPages).fill(0).map((x,i)=>i);
        this.currentSelectedPage = response.pageNumber;
      },err => throwError(err));
    })
  }


  nextClick(){
    if(this.currentSelectedPage < this.totalPages-1){
      this.getPage(++this.currentSelectedPage,18,this.name,this.city,this.country);
    }  
  }

  previousClick(){
    if(this.currentSelectedPage > 0){
      this.getPage(--this.currentSelectedPage,18,this.name,this.city,this.country);
    }  
  }

  getPaginationWithIndex(index: number) {
    this.getPage(index, 18,this.name,this.city,this.country);
  }

  active(index: number) {
    if(this.currentSelectedPage == index ){
      return {
        active: true
      };
    }
  }

  onSearchChange(searchValue: string): void { 
    console.log(searchValue) 
    this.name = searchValue;
    this.getPage(0,18,this.name,this.city,this.country);
  }

  goToProfile(username : string){
    this.router.navigateByUrl('/profile/'+username);
  }

  filterByPlace(){
    this.getPage(0,18,"",this.city,this.country);
  }

}
