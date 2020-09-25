import { Component, OnInit } from '@angular/core';
import { NgbDate } from '@ng-bootstrap/ng-bootstrap';
import { FormGroup ,FormBuilder, Validators} from '@angular/forms';
import {DomSanitizer} from '@angular/platform-browser';
import {AuthService} from '../../auth/shared/auth.service'
import { throwError } from 'rxjs';
import {TrainingsService} from '../../trainings/trainings.service';

@Component({
  selector: 'app-create-course',
  templateUrl: './create-course.component.html',
  styleUrls: ['./create-course.component.css']
})
export class CreateCourseComponent implements OnInit {

  courseForm : FormGroup;
  ///courseTime = {hour: 0, minute: 0};
  //courseDuration = {hour: 0, minute: 0};

  categories = ['Computer science','History','Communication','Data science','Cloud computing','Economics','Biology','Art','Physics','Psychiatry','Fiance','Medicine','Philosophy']

  file : File;
  public imagePath;
  imgURL: any;
  public message: string;



  constructor(private formBuilder : FormBuilder,public _DomSanitizationService: DomSanitizer,
    private courseService : TrainingsService) { }

  ngOnInit(): void {

    const today = new Date();
    this.courseForm = this.formBuilder.group({
      courseImg : [''],
      name : [''],
      description : [''],
      link : [''],
      location : [''],
      category : [''],
      courseTime : [{hour: 0, minute: 0}],
      courseDuration : [{hour: 0, minute: 0}],
      courseDate : [new NgbDate(today.getFullYear(),today.getMonth()+1,today.getDate())]
    })
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

  createCourse(){
    let date = this.courseForm.get('courseDate').value;
    let dateFormat = new Date(date.year,date.month-1,date.day);

    let time = this.courseForm.get('courseTime').value;
    let duration = this.courseForm.get('courseDuration').value
    let timeFormat = time.hour+' h '+time.minute;
    let durationFormat = duration.hour+' h '+duration.minute+' min';
    const uploadCourse = new FormData();
    uploadCourse.append('courseImg',this.courseForm.get('courseImg').value);
    uploadCourse.append('name',this.courseForm.get('name').value);
    uploadCourse.append('description',this.courseForm.get('description').value);
    uploadCourse.append('link',this.courseForm.get('link').value);
    uploadCourse.append('location',this.courseForm.get('location').value);
    uploadCourse.append('duration',durationFormat);
    uploadCourse.append('time',timeFormat);
    uploadCourse.append('date',dateFormat.toISOString());
    uploadCourse.append('category',this.courseForm.get('category').value);
    this.courseService.createCourse(uploadCourse);
    console.log(dateFormat.toISOString())
  }


}
