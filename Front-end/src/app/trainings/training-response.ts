import {CourseModel} from './training.payload';

export class CourseResponse {
    
    courses : CourseModel[];
    totalPages: number;
    pageNumber: number;
    pageSize: number;
}