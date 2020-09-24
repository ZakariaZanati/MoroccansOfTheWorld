import { Component, OnInit } from '@angular/core';
import { FormGroup ,FormBuilder, Validators} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import {TrainingsService} from './trainings.service';
import {DomSanitizer} from '@angular/platform-browser';
import {CourseResponse} from './training-response';
import {CourseModel} from './training.payload';
import {AuthService} from '../auth/shared/auth.service'
import { throwError } from 'rxjs';

@Component({
  selector: 'app-trainings',
  templateUrl: './trainings.component.html',
  styleUrls: ['./trainings.component.css']
})
export class TrainingsComponent implements OnInit {

  

  show : boolean = false;
  showForm : boolean = false;
  courseForm : FormGroup;
  file : File;
  showLinkField : boolean = true;
  showLocationField : boolean = true;

  categories = ['Computer science','History','Communication','Data science','Cloud computing'
                                ,'Economics','Biology','Art','Physics','Psychiatry','Fiance','Medicine','Philosophy']
  
  public imagePath;
  imgURL: any;
  public message: string;


  courses : Array<CourseModel> = [];
  userType : string;
  currentSelectedPage : number = 0;
  pageNumber : number = 0;
  totalPages : number = 0;
  pageIndexes : Array<number> = [];
  name : string = "";
  categoryFilter : string = "";
  dateFilter : Date;

  constructor(private formBuilder : FormBuilder,private http:HttpClient,
    private courseService : TrainingsService,public _DomSanitizationService: DomSanitizer,private authService : AuthService ) {
      this.userType = authService.getUserType(); 
    }

  ngOnInit(): void {

    this.courseForm = this.formBuilder.group({
      courseImg : [''],
      name : [''],
      description : [''],
      link : [''],
      location : [''],
      date : [''],
      duration : [''],
      category : ['']
    })

    this.getPage(0,4,this.name,this.categoryFilter,this.dateFilter);

  }

  public onFileChanged(event) {
    //Select File
    this.file = event.target.files[0];
    this.courseForm.get('courseImg').setValue(this.file);
    //this.image = "data:image/jpeg;base64,"+this.file;
  }

  preview(files) {
    if (files.length === 0)
      return;
 
    var mimeType = files[0].type;
    if (mimeType.match(/image\/*/) == null) {
      this.message = "Only images are supported.";
      return;
    }
 
    var reader = new FileReader();
    this.imagePath = files;
    reader.readAsDataURL(files[0]); 
    reader.onload = (_event) => { 
      this.imgURL = reader.result; 
    }
  }

  cancel(){
    this.show = !this.show
  }


  addCourse(){
    this.showForm =!this.showForm
  }

  createCourse(){
    const uploadCourse = new FormData();
    uploadCourse.append('courseImg',this.courseForm.get('courseImg').value);
    uploadCourse.append('name',this.courseForm.get('name').value);
    uploadCourse.append('description',this.courseForm.get('description').value);
    uploadCourse.append('link',this.courseForm.get('link').value);
    uploadCourse.append('location',this.courseForm.get('location').value);
    uploadCourse.append('duration',this.courseForm.get('duration').value);
    uploadCourse.append('date',this.courseForm.get('date').value);
    uploadCourse.append('category',this.courseForm.get('category').value);
    this.courseService.createCourse(uploadCourse);
    window.location.reload();
  }

  showLink(){
    this.showLinkField = true;
    this.showLocationField = false;
  }
  showLocation(){
    this.showLocationField = true;
    this.showLinkField = false;
  }

  getPage(page : number,size : number,name : string,category : string, date : Date){
    this.courseService.getCoursesPage(page,size,name,category,date).subscribe((response : CourseResponse) => {
      this.courses = response.courses;
      console.log(response)
      this.courses.map(cours => {
        const img = cours.imageBytes;
        if (img) {
          cours.imageBytes = "data:image/jpeg;base64,"+cours.imageBytes
        }
        this.totalPages = response.totalPages;
        this.pageIndexes = Array(this.totalPages).fill(0).map((x,i)=>i);
        this.currentSelectedPage = response.pageNumber;
      },err => throwError(err));
    })
  }

  nextClick(){
    if(this.currentSelectedPage < this.totalPages-1){
      this.getPage(++this.currentSelectedPage,4,this.name,this.categoryFilter,this.dateFilter);
    }  
  }

  previousClick(){
    if(this.currentSelectedPage > 0){
      this.getPage(--this.currentSelectedPage,4,this.name,this.categoryFilter,this.dateFilter);
    }  
  }

  getPaginationWithIndex(index: number) {
    this.getPage(index, 4,this.name,this.categoryFilter,this.dateFilter);
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
    this.getPage(0,4,this.name,this.categoryFilter,this.dateFilter);
  }
}
