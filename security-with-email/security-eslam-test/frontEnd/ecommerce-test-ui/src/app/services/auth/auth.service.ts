import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UserStorageService } from '../storage/user-storage.service';
import { catchError, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private BASE_URL ="http://localhost:8080/api/v1/auth";

  constructor(private http:HttpClient, private userStorageService: UserStorageService) { }


  // http://localhost:8080/api/v1/auth/register
  register(registerDTO:any): Observable<any>{
     return this.http.post<any>(`${this.BASE_URL}/register`,registerDTO);
  }



  // http://localhost:8080/api/v1/auth/login
  // login(username:string, password:string): any{
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json');
  //   const body = {username, password};

  //   return this.http.post<any>(`${this.BASE_URL}/login`,body,  { headers, observe: 'response' }).pipe(
  //     map(res =>{
  //       const token = res.headers.get('authorization')?.substring(7);
  //       const user = res.body;
  //       if(token && user){
  //         this.userStorageService.saveToken(token);
  //         this.userStorageService.saveUser(user);
  //         return true;
  //       }
  //       return false;
  //     })
  //   )

  // }

  login(usernameOrEmail: string, password: string): Observable<any> {
    const credentials = { usernameOrEmail, password };

    return this.http.post<any>(`${this.BASE_URL}/login`, credentials).pipe(
        tap(response => {
          const user = response.credentials;
          // Assuming the server returns a token upon successful login
          if (response.token) {
            // localStorage.setItem('token', response.token);
            // localStorage.setItem('user', JSON.stringify(response.role));
            this.userStorageService.saveToken(response.token);
            // this.userStorageService.saveUser(response.role);
            this.userStorageService.saveUser(response);
          }
        }),
        catchError(error => {
          // Handle login error
          console.error('Login failed:', error);
          return of(false);
        })
      );
  }











}
