import { Component, Input, OnInit } from '@angular/core';
import {CourseModel} from '../../trainings/training.payload';

@Component({
  selector: 'app-view-course',
  templateUrl: './view-course.component.html',
  styleUrls: ['./view-course.component.css']
})
export class ViewCourseComponent implements OnInit {


  @Input() course : CourseModel;

  constructor() { }

  ngOnInit(): void {
  }

  cancel(){
    this.course.show = false;
  }

}
