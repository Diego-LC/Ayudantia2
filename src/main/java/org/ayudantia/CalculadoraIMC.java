package org.ayudantia;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class CalculadoraIMC {
    private static int contador = 1;
    public static void main(String[] args) {
        menu(); // Inicia el programa
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in); // Crea una instancia de Scanner para leer la entrada del usuario

        while (contador <= 3) {
            System.out.println("Ingrese los datos del niño " + contador + "\n----------------");
            try {
                ingresarDatos(sc); // Solicita al usuario ingresar los datos del niño
            } catch (InputMismatchException e) {
                System.out.println("Error: " + e);
                sc.nextLine(); // Limpia el buffer de entrada
                contador--; // Vuelve a solicitar los datos del mismo niño
            }
            contador++;
        }

        sc.close(); // Cierra la instancia de Scanner al finalizar
    }

    public static void ingresarDatos(Scanner sc) {
        System.out.print("Ingrese el nombre del niño: ");
        String nombre = sc.nextLine();
        
        if (verificadorNombre(nombre)) { // Verifica si el nombre es válido
            System.out.print("Ingrese el peso del niño  entre 3,0 y 100,0 (en kg): ");
            double peso = recibirRespuestaDelUsuario(sc); // Solicita al usuario ingresar el peso
            System.out.print("Ingrese la altura del niño en metros  entre 0,4 y 2 (ej 1,3): ");
            double altura = recibirRespuestaDelUsuario(sc); // Solicita al usuario ingresar la altura
            calcularIMC(nombre, peso, altura); // Calcula el IMC y clasifica al niño
        } else {
            ingresarDatos(sc); // Vuelve a solicitar el nombre del niño si no es válido
        }
    }

    public static double recibirRespuestaDelUsuario(Scanner sc) {
        try {
            return sc.nextDouble();
        } catch (InputMismatchException exception) {
            System.out.println("Error: Solo ingresar numeros o decimales separados por coma (ej 0,85)\n");
            sc.nextLine(); // Limpia el buffer de entrada
            System.out.println("Vuelva a ingresar el último dato");
            return recibirRespuestaDelUsuario(sc); // Vuelve a solicitar el valor al usuario
        }
    }

    public static boolean verificadorNombre(String nombre) {
        if (nombre == null || nombre.length() < 3 || nombre.matches(".*\\d+.*")) {
            System.out.println("\nEl nombre no puede ser nulo, tener menos de 3 caracteres o contener números");
            return false;
        }
        return true;
    }

    public static boolean verificadorPeso(double peso) {
        if (peso < 3 || peso > 100) {
            System.out.println("\nEl peso debe estar entre 3 y 100");
            return false;
        }
        return true;
    }

    public static boolean verificadorAltura(double altura) {
        if (altura < 0.4 || altura > 2) {
            System.out.println("\nLa altura debe estar entre 0.4 y 2");
            return false;
        }
        return true;
    }

    public static void calcularIMC(String nombre, double peso, double altura) {
        try {
            if (verificadorPeso(peso) && verificadorAltura(altura)) {
                double imc = peso / (altura * altura);
                clasificarIMC(imc, nombre); // Clasifica al niño según su IMC
            }else {
                System.out.println("\nVuela a ingresar los datos\n"); // Vuelve a solicitar los datos del niño
                contador--; // Vuelve a solicitar los datos del mismo niño
            }
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void clasificarIMC(double imc, String nombre) {
        System.out.println("-----\nResultado: ");
        if (imc < 18.5) {
            System.out.println("El niño " + nombre + " tiene bajo peso\n");
        } else if (imc >= 18.5 && imc < 24.9) {
            System.out.println("El niño " + nombre + " tiene un peso normal\n");
        } else if (imc >= 25 && imc < 29.9) {
            System.out.println("El niño " + nombre + " tiene sobrepeso\n");
        } else if (imc >= 30) {
            System.out.println("El niño " + nombre + " tiene obesidad\n");
        }
    }
}