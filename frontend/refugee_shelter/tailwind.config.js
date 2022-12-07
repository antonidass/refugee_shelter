/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    fontFamily: {
      sans: ["Yandex Sans Display"],
      serif: ["Yandex Sans Display"],
      mono: ["Yandex Sans Display"],
    },
    extend: {
      boxShadow: {
        "3xl": "0 35px 60px -15px rgba(0, 0, 0, 0.3)",
      },
    },
  },
  daisyui: {
    themes: [
      {
        mytheme: {
          primary: "#112D32",
          secondary: "#254E58",
          neutral: "#88BDBC",
          ghost: "#FEFFFF",
          borderColor: "#DDDDDD",
          info: "#57BA98",
          error: "#C96567",
        },
      },
    ],
  },
  plugins: [require("daisyui")],
};
