import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserService } from './user.service';

@Injectable()
export class BasicAuthInterceptorInterceptor implements HttpInterceptor {

  constructor(private userService: UserService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with basic auth credentials if available
        const currentUser = this.userService.getConnectedUserAuthData();
        if (currentUser) {
            request = request.clone({
                setHeaders: { 
                    Authorization: `Basic ${currentUser}`
                }
            });
        }

        return next.handle(request);
    }
}
