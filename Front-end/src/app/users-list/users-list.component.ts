import { Component, OnInit } from '@angular/core';
import { throwError } from 'rxjs';
import {ConnectionsService} from '../connections/connections.service';
import {UsersResponse} from './users-response';
import {UserModel} from './users-model';
import { Router } from '@angular/router';
import {CscService} from '../csc/csc.service';

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

  countries: {};
  states: {};
  cities: {};

  constructor(private connectionsService : ConnectionsService,private router : Router,private cscService : CscService) { }

  ngOnInit(): void {

    this.cscService.getCountries().subscribe(
      
      data => {this.countries = data;console.log(data);}
    );

    this.getPage(0,18,this.name,this.city,this.country);
  }

  getPage(page : number,size : number,name : string,city : string, country : string){
    this.connectionsService.getUsersPage(page,size,name,city,country).subscribe((response : UsersResponse) => {
      this.users = response.users.filter(user => user.username != 'admin');
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

  onChangeCountry(countryId: number) {
    console.log(this.country);
    if (countryId) {
      this.cscService.getStates(countryId).subscribe(
        data => {
          this.states = data;
          this.cities = null;
        }
      );
    } else {
      this.states = null;
      this.cities = null;
    }
  }

  onChangeState(stateId: number) {
    if (stateId) {
      this.cscService.getCities(stateId).subscribe(
        data => this.cities = data
      );
    } else {
      this.cities = null;
    }
  }

}
