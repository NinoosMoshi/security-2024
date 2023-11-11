import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {

  signupForm!: FormGroup;
  hidePassword:boolean = true;


  constructor(private fb: FormBuilder,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    private router: Router){}


ngOnInit(): void{
  this.signupForm = this.fb.group({
      name:[null, [Validators.required]],
      username:[null, [Validators.required]],
      email:[null, [Validators.required]],
      password:[null, [Validators.required]],
      confirmPassword:[null, [Validators.required]]
      })
}



togglePasswordVisibility(){
this.hidePassword = !this.hidePassword;
}


onSubmit(){
  const password = this.signupForm.get('password')?.value;
  const confirmPassword = this.signupForm.get('confirmPassword')?.value;

  if(password !== confirmPassword){
    this.snackBar.open('Passwords do not match.', 'Close', { duration: 5000, panelClass: 'error-snackbar' });
    return;
  }
  this.authService.register(this.signupForm.value).subscribe({
    next:res =>{
      this.snackBar.open('Sign up successful!.', 'Close', { duration: 5000 });
      this.router.navigateByUrl("/login")
    },
    error:err =>{
      this.snackBar.open('Sign up faild. Please try again.', 'Close', { duration: 5000, panelClass: 'error-snackbar' });
    }
  })

}


}
