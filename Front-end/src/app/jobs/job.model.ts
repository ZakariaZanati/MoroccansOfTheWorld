export interface JobModel {

    id? : number;
    post : string;
    enterprise : string;
    location : string;
    contractType : string;
    description : string;
    qualifications? : string;
    salary? : string;
    creationDate : string;
    expirationDate? : string;
    author : string;
    link? : string;
}