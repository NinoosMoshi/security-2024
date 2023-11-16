import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { CodeActivationComponent } from './code-activation/code-activation.component';
import { accountGuard } from './services/canActive/account.guard';
import { loginGuard } from './services/canActive/login.guard';
import { ResetPasswordComponent } from './reset-password/reset-password.component';


const routes: Routes = [
 {path:'reset', component:ResetPasswordComponent},
 { path:'active-code', component:CodeActivationComponent, canActivate:[accountGuard]},
 {path:'login', component:LoginComponent, canActivate:[loginGuard]},
 {path:'signup', component:SignupComponent, canActivate:[loginGuard]},
 { path: 'customer', loadChildren: () => import('./customer/customer.module').then(m => m.CustomerModule) },
 { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
