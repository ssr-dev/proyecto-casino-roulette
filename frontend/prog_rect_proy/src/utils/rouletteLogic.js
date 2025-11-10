

export const ROULETTE_NUMBERS = [
  { number: 0, color: "green" },
  { number: 32, color: "red" },
  { number: 15, color: "black" },
  { number: 19, color: "red" },
  { number: 4, color: "black" },
  { number: 21, color: "red" },
  { number: 2, color: "black" },
  { number: 25, color: "red" },
  { number: 17, color: "black" },
  { number: 34, color: "red" },
  { number: 6, color: "black" },
  { number: 27, color: "red" },
  { number: 13, color: "black" },
  { number: 36, color: "red" },
  { number: 11, color: "black" },
  { number: 30, color: "red" },
  { number: 8, color: "black" },
  { number: 23, color: "red" },
  { number: 10, color: "black" },
  { number: 5, color: "red" },
  { number: 24, color: "black" },
  { number: 16, color: "red" },
  { number: 33, color: "black" },
  { number: 1, color: "red" },
  { number: 20, color: "black" },
  { number: 14, color: "red" },
  { number: 31, color: "black" },
  { number: 9, color: "red" },
  { number: 22, color: "black" },
  { number: 18, color: "red" },
  { number: 29, color: "black" },
  { number: 7, color: "red" },
  { number: 28, color: "black" },
  { number: 12, color: "red" },
  { number: 35, color: "black" },
  { number: 3, color: "red" },
  { number: 26, color: "black" },
];



export const getWinningNumber = () => {
  const randomIndex = Math.floor(Math.random() * ROULETTE_NUMBERS.length);
  return ROULETTE_NUMBERS[randomIndex];
};



export const calculateWinnings = (bets, winningNumber, initialCredits) => {
  let totalWinnings = 0;
  let totalBetAmount = 0;

  for (const bet of bets) {
    totalBetAmount += bet.amount;
    const { type, value, amount } = bet;

    let won = false;

    switch (type) {
      case "number":
        if (winningNumber.number === value) {
          won = true;
          totalWinnings += amount * 35; // 35:1 payout for single number
        }
        break;
      case "color":
        if (winningNumber.color === value && winningNumber.number !== 0) {
          won = true;
          totalWinnings += amount * 2; // 1:1 payout for color
        }
        break;
      case "parity":
        if (winningNumber.number !== 0) {
          if (value === "even" && winningNumber.number % 2 === 0) {
            won = true;
            totalWinnings += amount * 2; // 1:1 payout for even
          } else if (value === "odd" && winningNumber.number % 2 !== 0) {
            won = true;
            totalWinnings += amount * 2; // 1:1 payout for odd
          }
        }
        break;

      case "range":
        if (winningNumber.number !== 0) {
          if (
            value === "1to18" &&
            winningNumber.number >= 1 &&
            winningNumber.number <= 18
          ) {
            won = true;
            totalWinnings += amount * 2; // 1:1 payout for 1-18
          } else if (
            value === "19to36" &&
            winningNumber.number >= 19 &&
            winningNumber.number <= 36
          ) {
            won = true;
            totalWinnings += amount * 2; // 1:1 payout for 19-36
          }
        }
        break;
        
      case "dozen":
        if (winningNumber.number !== 0) {
          if (
            value === "1st12" &&
            winningNumber.number >= 1 &&
            winningNumber.number <= 12
          ) {
            won = true;
            totalWinnings += amount * 3; // 2:1 payout for dozen
          } else if (
            value === "2nd12" &&
            winningNumber.number >= 13 &&
            winningNumber.number <= 24
          ) {
            won = true;
            totalWinnings += amount * 3; // 2:1 payout for dozen
          } else if (
            value === "3rd12" &&
            winningNumber.number >= 25 &&
            winningNumber.number <= 36
          ) {
            won = true;
            totalWinnings += amount * 3; // 2:1 payout for dozen
          }
        }
        break;

      case "column":
        if (winningNumber.number !== 0) {
          const column1 = [1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34];
          const column2 = [2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35];
          const column3 = [3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36];
          if (value === "col1" && column1.includes(winningNumber.number)) {
            won = true;
            totalWinnings += amount * 3; // 2:1 payout for column
          } else if (
            value === "col2" &&
            column2.includes(winningNumber.number)
          ) {
            won = true;
            totalWinnings += amount * 3; // 2:1 payout for column
          } else if (
            value === "col3" &&
            column3.includes(winningNumber.number)
          ) {
            won = true;
            totalWinnings += amount * 3; // 2:1 payout for column
          }
        }
        break;
      default:
        break;
    }
  }

  const netChange = totalWinnings - totalBetAmount;
  const newCredits = initialCredits + netChange;

  return {
    winnings: totalWinnings,
    netChange: netChange,
    newCredits: newCredits,
  };
};
