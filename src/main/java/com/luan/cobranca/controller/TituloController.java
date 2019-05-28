package com.luan.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.luan.cobranca.model.StatusTitulo;
import com.luan.cobranca.model.Titulo;
import com.luan.cobranca.repository.Titulos;
import com.luan.cobranca.repository.filter.TituloFilter;
import com.luan.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController
{
    private static final String CADASTRO_TITULO = "CadastroTitulo";

    @Autowired
    private CadastroTituloService cadastrotituloService;

    @RequestMapping("/novo")
    public ModelAndView novo()
    {
        ModelAndView mv = new ModelAndView(CADASTRO_TITULO);
        mv.addObject(new Titulo());

        return mv;
    }

    @RequestMapping
    public ModelAndView pesquisa(@ModelAttribute("filtro") TituloFilter filtro)
    {
        //String descricao = filtro.getDescricao() == null ? "" : filtro.getDescricao();
        List<Titulo> titulosList = cadastrotituloService.filtrarTitulos(filtro.getDescricao());

        //titulosList = titulos.findAll(Sort.by("codigo"));

        ModelAndView mv = new ModelAndView("PesquisaTitulos");
        mv.addObject("titulos", titulosList);
        return mv;
    }

    @RequestMapping("{codigo}")
    public ModelAndView edicao(@PathVariable("codigo") Titulo titulo)
    {
        ModelAndView mv = new ModelAndView(CADASTRO_TITULO);
        mv.addObject(titulo);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes)
    {
        if (errors.hasErrors()) {
            return CADASTRO_TITULO;
        }
        try {
            cadastrotituloService.salvar(titulo);
        } catch (IllegalArgumentException e) {
            errors.reject("dataVencimento", null, e.getMessage());
            return CADASTRO_TITULO;
        }
        attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");

        return "redirect:titulos/novo";
    }

    @RequestMapping(value = "{codigo}", method = RequestMethod.DELETE)
    public String excluir(@PathVariable Long codigo, RedirectAttributes attributes)
    {
        cadastrotituloService.excluir(codigo);
        attributes.addFlashAttribute("mensagem", "Título excluido com sucesso!");
        return "redirect:/titulos";
    }

    @RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
    public @ResponseBody String receberTitulo(@PathVariable Long codigo)
    {
        cadastrotituloService.receber(codigo);
        return StatusTitulo.RECEBIDO.getDescricao();
    }

    @ModelAttribute("statusTitulos")
    public List<StatusTitulo> leiaTodosStatusTitulo()
    {
        return Arrays.asList(StatusTitulo.values());
    }
}
