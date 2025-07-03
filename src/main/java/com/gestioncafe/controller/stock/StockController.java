package com.gestioncafe.controller.stock;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.gestioncafe.service.*;
import com.gestioncafe.repository.*;
import com.gestioncafe.model.*;

@Controller
@RequestMapping("/administratif/stock")
public class StockController {

    @GetMapping
    public String index() {
        return "administratif/stock/index";
    }
}
