import { Injectable } from '@angular/core';
import {catchError, map, Observable, throwError} from 'rxjs';
import {Register} from '../models/register.model';
import {Login} from '../models/login.model';
import {AbstractService} from './abstract.service';

@Injectable({
    providedIn: 'root'
})
export class AuthService extends AbstractService {

    override servicePrefix = '/auth';

    login(user: Login): Observable<any> {
        const url = `${this.backBaseUrl}${this.servicePrefix}/signin`;
        const body = {
            email: user.email,
            password: user.password,
        };

        return this.httpClient.post(url, body).pipe(
            map((response: any) => {
                const token = response.userToken;
                this.storeToken(token);
                return response;
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

    storeToken(token: string) {
        const expires = new Date();
        expires.setTime(expires.getTime() + 7 * 24 * 60 * 60 * 1000); // 7 days
        document.cookie = `userToken=${token}; expires=${expires.toUTCString()}; path=/; `;
        //secure; samesite=strict`; // Not sure why it doesn't work with it...
    }

    getTokenFromCookies(): string | null {
        const name = 'userToken=';
        const cookie = document.cookie.split(';').find(c => c.trim().startsWith(name));
        return cookie ? cookie.trim().substring(name.length) : null;
    }

    logout() { // TODO to check
        document.cookie = 'userToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/; ';
      }

      isLoggedIn(): boolean {
        return this.getTokenFromCookies() !== null;
      }

    closeAllSessions() { // TODO Implement closeAllSessions
        throw new Error('Method not implemented.');
    }

    registerUser(newUser: Register): Observable<any> { // TODO Not tested yet
        const url = `${this.backBaseUrl}${this.servicePrefix}/signup`;

        const body = {
            email: newUser.email,
            username: newUser.username,
            password: newUser.password,
        };

        return this.httpClient.post(url, body).pipe(
            map((response: any) => {
                const token = response.userToken;
                this.storeToken(token);
                return response;
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

}
