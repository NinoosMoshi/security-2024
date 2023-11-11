import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private CATEGORY_URL = 'http://localhost:8080/api/admin/categories';
  private PRODUCT_URL = 'http://localhost:8080/api/admin/products';

  constructor(private http:HttpClient) { }

 // http://localhost:8080/api/admin/categories/save
  addCategory(categoryDTO:any):Observable<any>{
    return this.http.post<any>(`${this.CATEGORY_URL}/save`, categoryDTO, {
      headers: this.createAuthorizationHeader()
    })
  }



  // http://localhost:8080/api/admin/categories/get-all
  getAllCategories():Observable<any>{
    return this.http.get<any>(`${this.CATEGORY_URL}/get-all`, {
      headers: this.createAuthorizationHeader()
    })
  }


  // http://localhost:8080/api/admin/products/save
  addProduct(productDTO:any):Observable<any>{
    return this.http.post<any>(`${this.PRODUCT_URL}/save`, productDTO, {
      headers: this.createAuthorizationHeader()
    })
  }


  // http://localhost:8080/api/admin/products/get-all
  getAllProducts():Observable<any>{
    return this.http.get<any>(`${this.PRODUCT_URL}/get-all`, {
      headers: this.createAuthorizationHeader()
    })
  }




  private createAuthorizationHeader(): HttpHeaders{
     return new HttpHeaders().set(
       'Authorization', 'Bearer ' + UserStorageService.getToken()
     )
  }

}
