import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CatalogItem } from '../models/catalog-item.model';

@Injectable({
  providedIn: 'root'
})
export class CatalogService {
  // URL base da API do serviço de catálogo
  private apiUrl = 'http://localhost:8080/catalog';

  constructor(private http: HttpClient) { }

  /**
   * Busca o catálogo de filmes para um usuário específico
   * @param userId ID do usuário
   * @returns Observable com a lista de itens do catálogo
   */
  getCatalogByUserId(userId: string): Observable<CatalogItem[]> {
    // Faz uma requisição GET ao backend
    return this.http.get<CatalogItem[]>(`${this.apiUrl}/${userId}`);
  }
}
