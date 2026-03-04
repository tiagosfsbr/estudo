import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CatalogService } from '../services/catalog.service';
import { CatalogItem } from '../models/catalog-item.model';

@Component({
  selector: 'app-catalog',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './catalog.component.html',
  styleUrl: './catalog.component.css'
})
export class CatalogComponent implements OnInit {
  // Lista de itens do catálogo
  catalogItems: CatalogItem[] = [];
  
  // ID do usuário para buscar catálogo
  userId: string = '';
  
  // Indicador de carregamento
  isLoading: boolean = false;
  
  // Mensagem de erro
  errorMessage: string = '';

  constructor(private catalogService: CatalogService) { }

  ngOnInit(): void {
    // Inicialmente busca o catálogo do usuário padrão
    this.userId = 'user123';
    this.loadCatalog();
  }

  /**
   * Carrega o catálogo de filmes para o usuário
   */
  loadCatalog(): void {
    // Valida se o userId foi preenchido
    if (!this.userId.trim()) {
      this.errorMessage = 'Por favor, informe um ID de usuário';
      return;
    }

    // Inicia o carregamento
    this.isLoading = true;
    this.errorMessage = '';
    this.catalogItems = [];

    // Faz a chamada ao serviço
    this.catalogService.getCatalogByUserId(this.userId).subscribe({
      // Caso de sucesso
      next: (data: CatalogItem[]) => {
        this.catalogItems = data;
        this.isLoading = false;
      },
      // Caso de erro
      error: (error) => {
        console.error('Erro ao buscar catálogo:', error);
        this.errorMessage = 'Erro ao buscar catálogo. Verifique se o servidor está rodando em http://localhost:8080';
        this.isLoading = false;
      }
    });
  }

  /**
   * Formata o rating para exibição visual
   */
  getStars(rating: number): string {
    return '⭐'.repeat(Math.round(rating));
  }
}
