import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import {JobModel} from '../job.model';

@Component({
  selector: 'app-create-job-offer',
  templateUrl: './create-job-offer.component.html',
  styleUrls: ['./create-job-offer.component.css']
})
export class CreateJobOfferComponent implements OnInit {

  @ViewChild('f') jobForm : NgForm;

  submitted = false;
  jobModel : JobModel;

  constructor() { }

  ngOnInit() {
  }

  onSubmit(){
    this.jobModel = this.jobForm.value;
    console.log(this.jobModel);
  }

}
