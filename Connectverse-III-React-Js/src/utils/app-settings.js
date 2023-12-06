export const getLanguage = () => (
    localStorage.getItem("Language")
);

export const setLanguage = (language) => (
    localStorage.setItem("Language", language)
);
