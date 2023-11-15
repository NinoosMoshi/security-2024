import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UserStorageService } from '../storage/user-storage.service';

export const accountGuard: CanActivateFn = (route, state) => {

  const router = inject(Router);


  if(sessionStorage.getItem('emailAtive') == null){
    router.navigateByUrl("/signup")
    return false;
  }

  return true;



};
