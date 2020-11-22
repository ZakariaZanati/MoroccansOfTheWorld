import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import {JobModel} from '../job.model';
import {JobsService} from '../shared/jobs.service';

@Component({
  selector: 'app-create-job-offer',
  templateUrl: './create-job-offer.component.html',
  styleUrls: ['./create-job-offer.component.css']
})
export class CreateJobOfferComponent implements OnInit {

  @ViewChild('f') jobForm : NgForm;

  submitted = false;
  jobModel : JobModel;

  constructor(private jobsService : JobsService) { }

  ngOnInit() {
  }

  onSubmit(){
    this.jobModel = this.jobForm.value;
    console.log(this.jobModel);
    this.jobsService.createJobOffer(this.jobModel);
  }

}
