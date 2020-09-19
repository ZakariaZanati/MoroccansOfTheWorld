export interface LoginResponse {
    authenticationToken : string,
    refreshToken: string,
    expiresAt : Date,
    username : string,
    completed : boolean,
    fullName : string,
    userType : string,
    verified : boolean
}