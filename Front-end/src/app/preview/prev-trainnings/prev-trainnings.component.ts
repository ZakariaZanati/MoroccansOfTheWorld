import { Component, OnInit } from '@angular/core';
import {CourseResponse} from '../../trainings/training-response';
import {TrainingsService} from '../../trainings/trainings.service';
import {CourseModel} from '../../trainings/training.payload';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-prev-trainnings',
  templateUrl: './prev-trainnings.component.html',
  styleUrls: ['./prev-trainnings.component.css']
})
export class PrevTrainningsComponent implements OnInit {

  courses : Array<CourseModel> = [];

  constructor(private service : TrainingsService) { }

  ngOnInit(): void {
    this.getPage(0,4,"","",undefined);
  }

  getPage(page : number,size : number,name : string,category : string, date : Date){
    this.service.getCoursesPage(page,size,name,category,date).subscribe((response : CourseResponse) => {
      this.courses = response.courses;
      console.log(response)
      this.courses.map(course => {
        course.show = false;
        course.dateTime = new Date(course.dateTime).toLocaleDateString()
        const img = course.imageBytes;
        if (img) {
          course.imageBytes = "data:image/jpeg;base64,"+course.imageBytes
        }
        
      },err => throwError(err));
    })
  }

}
