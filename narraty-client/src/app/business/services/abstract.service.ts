import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environment/environment';
import { Injectable } from '@angular/core';
@Injectable({
    providedIn: 'root'
})

export abstract class AbstractService {
  protected backBaseUrl = environment.BACK_BASE_URL;
    protected servicePrefix = '';
  protected constructor(protected httpClient: HttpClient) {}
}