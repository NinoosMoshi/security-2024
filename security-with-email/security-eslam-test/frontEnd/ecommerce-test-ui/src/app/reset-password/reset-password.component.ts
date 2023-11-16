import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent {

  emailForm!: FormGroup;
  resetForm!: FormGroup;
  hidePassword:boolean = true;
  enableFrom: boolean = true;


  constructor(private fb: FormBuilder,
    private authService:AuthService,
    private snackBar: MatSnackBar,
    private router: Router){}


  ngOnInit(): void{
    this.emailForm = this.fb.group({
        email:[null, [Validators.required]],
        })

    this.resetForm = this.fb.group({
        code:[null, [Validators.required]],
        password:[null, [Validators.required]],
        confirmPassword:[null, [Validators.required]]
        })
  }


  togglePasswordVisibility(){
    this.hidePassword = !this.hidePassword;
    }


  onSubmitEmail(){
     const email = this.emailForm.get('email')?.value;

     this.authService.checkEmail(email).subscribe({
      next:res =>{
        if(res.result == 1){
          this.enableFrom = false;
        }
      },
      error:err =>{
        alert("Email is not Exists")
      }
     })

   }




  onSubmitReset(){

    const email = this.emailForm.get('email')?.value;
    const code = this.resetForm.get('code')?.value;
    const password = this.resetForm.get('password')?.value;
    const confirmPassword = this.resetForm.get('confirmPassword')?.value;

    if(password !== confirmPassword){
      this.snackBar.open('Passwords do not match.', 'Close', { duration: 5000, panelClass: 'error-snackbar' });
      return;
    }

    this.authService.resetPassword(email, password, code).subscribe({

      next:res =>{
        if(res.result == 1){
           this.router.navigateByUrl("/login")
        }
      },
      error:err =>{
        alert("Invalid Code")
      }
    })



   }






}
