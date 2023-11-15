import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UserStorageService } from '../storage/user-storage.service';

export const loginGuard: CanActivateFn = (route, state) => {

  const router = inject(Router);

  if(UserStorageService.isCustomerLoggedIn()){
    router.navigateByUrl("/customer/dashboard");
    return false;
  }

  return true;

};
